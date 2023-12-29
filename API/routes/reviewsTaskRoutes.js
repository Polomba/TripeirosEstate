'use strict'
const express = require('express');
const reviewTaskController = require('../controllers/reviewTaskController')
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const {getReviewsTask, addReviewTask, updateReviewTask, deleteReviewTask} = reviewTaskController;

router.get('/ReviewsTask', checkRoles.checkRoleTarefa,  getReviewsTask);
router.get('/ReviewsTask',checkRoles.checkRoleTarefa,  getReviewsTask);

router.post('/ReviewTask/:taskId', addReviewTask);

router.put('/ReviewTask/:taskId', checkRoles.checkRoleTarefa, updateReviewTask);

router.delete('/ReviewTask/:reviewId',deleteReviewTask);

module.exports = {
    routes: router
}