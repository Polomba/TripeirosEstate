'use strict'

const commentTaskData = require('../data/commentTaskService');
const utils = require('../utils/utils');

const listComments = async (req, res) => {
    try {
        const revP = await commentTaskData.listComments();
        res.send(revP);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addCommentTask = async (req, res)=> {
    try {
        const taskId = req.params.taskId;
        const data = req.body;
        const created = await commentTaskData.createCommentTask(taskId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateCommentTask = async (req, res)=> {
    try {
        const taskId = req.params.taskId;
        const data = req.body;
        const created = await commentTaskData.updateCommentTask(taskId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteCommentTask = async (req, res)=> {
    try {
        const commentId = req.params.commentId;
        const data = req.body;
        const deleted = await commentTaskData.deleteCommentTask(commentId,data);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getCommentsByTaskId = async (req, res) => {
    try {
        const taskId = req.params.taskId;
        const comments = await commentTaskData.listCommentsByTaskId(taskId);

        res.json(comments);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

module.exports = {
    listComments,
    addCommentTask,
    updateCommentTask,
    deleteCommentTask,
    getCommentsByTaskId
}