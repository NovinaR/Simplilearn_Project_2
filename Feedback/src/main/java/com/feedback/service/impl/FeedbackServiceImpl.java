package com.feedback.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.feedback.exception.BusinessException;
import com.feedback.dao.FeedbackDAO;
import com.feedback.model.Feedback;
import com.feedback.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{
	
	@Autowired
	private FeedbackDAO dao;

	@Override
	public Feedback generateFeedback(Feedback feedback) {
		
		return dao.save(feedback);
	}
	
	@Override
	public Feedback updateFeedback(Feedback feedback,int id) throws BusinessException{
		Optional<Feedback> feedbackPresent=dao.findById(id);
		if(!feedbackPresent.isPresent()) {
			throw new BusinessException("No Feedback details present for id "+id);
		}
		feedback.setId(id);
		return dao.save(feedback);
	}

	@Override
	public Feedback getFeedbackById(int id) throws BusinessException{
		
		if(id<=0) {
			throw new BusinessException("Id "+id +" is invalid");
		}
		Feedback feedback=null;
		try {
		feedback=dao.findById(id).get();
		}catch(NoSuchElementException e) {
			throw new BusinessException("No Feedback details found for id "+id);
		}
		return feedback;
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		
		return dao.findAll();
	}

	@Override
	public Feedback deleteFeedbackById(int id) throws BusinessException{

		if(id<=0) {
			throw new BusinessException("Id "+id +" is invalid");
		}
		try {
			dao.deleteById(id);;
		}catch(EmptyResultDataAccessException e) {
			throw new BusinessException("No Feedback details found for id "+id);
		}
		return null;
	}

	@Override
	public List<Feedback> getFeedbackByRatings(float ratings) {
		
		return dao.findByRatings(ratings);
	}

	@Override
	public List<Feedback> getFeedbackByCity(String city) {
		
		return dao.findByCity(city);
	}

	@Override
	public List<Feedback> getFeedbackByFeedback(String feedback) {
	
		return dao.findByFeedback(feedback);
	}

}