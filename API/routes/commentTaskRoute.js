'use strict'
const express = require('express');
const commentTaskController = require('../controllers/commentTaskController')
const router = express.Router();

const {listComments, getCommentsByTaskId, addCommentTask, updateCommentTask, deleteCommentTask} = commentTaskController;

router.get('/CommentTask',listComments);
router.get('/CommentTask/:taskId', getCommentsByTaskId);

router.post('/CommentTask/:taskId',addCommentTask);

router.put('/CommentTask/:commentId', updateCommentTask);

router.delete('/CommentTask/:commentId', deleteCommentTask);


module.exports = {
    routes: router
}