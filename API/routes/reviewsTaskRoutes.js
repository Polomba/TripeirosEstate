'use strict'
const express = require('express');
const reviewTaskController = require('../controllers/reviewTaskController')
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const {getReviewsTask, addReviewTask, updateReviewTask, deleteReviewTask} = reviewTaskController;

router.get('/ReviewsTask', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, getReviewsTask);

router.post('/ReviewTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, addReviewTask);

router.put('/ReviewTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, updateReviewTask);

router.delete('/ReviewTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, deleteReviewTask);


module.exports = {
    routes: router
}