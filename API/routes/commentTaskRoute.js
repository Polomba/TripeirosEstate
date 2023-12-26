'use strict'
const express = require('express');
const reviewTaskController = require('../controllers/reviewTaskController')
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const {getCommentsTask, getCommentsTaskByUserId, addCommentTask, updateCommentTask, deleteCommentTask} = reviewTaskController;

router.get('/CommentTask', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, getCommentsTask);
router.get('/CommentTaskUser/:userId', authCookie.authCookieVerify, getCommentsTaskByUserId);

router.post('/CommentTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, addCommentTask);

router.put('/CommentTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, updateCommentTask);

router.delete('/CommentTask/:userId', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, deleteCommentTask);


module.exports = {
    routes: router
}