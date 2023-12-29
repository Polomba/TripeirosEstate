'use strict'
const express = require('express');
const commentTaskController = require('../controllers/commentTaskController')
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const {listComments, getCommentsByTaskId, addCommentTask, updateCommentTask, deleteCommentTask} = commentTaskController;

router.get('/CommentTask',listComments);
router.get('/CommentTask/:taskId', getCommentsByTaskId);

router.post('/CommentTask/:taskId',addCommentTask);

router.put('/CommentTask/:taskId', updateCommentTask);

router.delete('/CommentTask/:commentId', deleteCommentTask);


module.exports = {
    routes: router
}