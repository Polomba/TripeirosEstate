'use strict'
const express = require('express');
const reviewTaskController = require('../controllers/reviewTaskController')
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const {getReviewsTask, addReviewTask, updateReviewTask, deleteReviewTask} = reviewTaskController;

router.get('/ReviewsTask',authCookie.authCookieVerify, checkRoles.checkRoleTarefa,  getReviewsTask);
router.get('/ReviewsTask/:taskId',authCookie.authCookieVerify, checkRoles.checkRoleTarefa,  getReviewsTask);

router.post('/ReviewTask/:taskId', addReviewTask);

router.put('/ReviewTask/:taskId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, updateReviewTask);

router.delete('/ReviewTask/:reviewId',deleteReviewTask);


module.exports = {
    routes: router
}