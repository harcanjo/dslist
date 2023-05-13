package com.harcanjo.dslist.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harcanjo.dslist.dto.GameListDTO;
import com.harcanjo.dslist.entities.GameList;
import com.harcanjo.dslist.projections.GameMinProjection;
import com.harcanjo.dslist.repositories.GameListRepository;
import com.harcanjo.dslist.repositories.GameRepository;

@Service
public class GameListService {

	@Autowired
	private GameListRepository gameListRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Transactional(readOnly = true)
	public List<GameListDTO> findAll(){
		List<GameList> result = gameListRepository.findAll();
		List<GameListDTO> dto = result.stream().map(x -> new GameListDTO(x)).toList();
		return dto;
	}
	
	// move on db belonging
	@Transactional
	public void move(Long listId, int sourceIndex, int destinationIndex) {
		List<GameMinProjection> list = gameRepository.searchByList(listId);
		
		// remove item from list
		GameMinProjection obj = list.remove(sourceIndex);
		// add item to list on destinationIndex
		list.add(destinationIndex, obj);
				
		int min = sourceIndex < destinationIndex ? sourceIndex : destinationIndex;
		int max = sourceIndex < destinationIndex ? destinationIndex : sourceIndex;
		
		for (int i = min; i <= max; i++) {
			gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
		}
		
		/* Endpoint
		 * 
		 * POST List replacement: {{host}}/list/2/replacement
		 * 
		 * body - json
		 * 
		 * {
		 * 		"sourceIndex":3,
		 * 		"destinationIndex":1
		 * }
		 * */
	}
}
