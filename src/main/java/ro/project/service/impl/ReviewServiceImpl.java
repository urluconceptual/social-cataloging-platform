package ro.project.service.impl;

import ro.project.model.Review;
import ro.project.service.ReviewService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReviewServiceImpl implements ReviewService {
    private static Map<UUID, Review> reviewMap = new HashMap<>();
}
