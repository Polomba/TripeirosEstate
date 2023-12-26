'use strict'

const commnentTaskData = require('../data/commentTaskService');
const utils = require('../utils/utils');

const getCommentsTask = async (req, res) => {
    try {
        const revP = await commnentTaskData.listCommentsTasks();
        res.send(revP);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addCommentTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const created = await commnentTaskData.createCommentTask(userId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateCommentTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const created = await commnentTaskData.updateCommentTask(userId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteCommentTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const deleted = await commnentTaskData.deleteCommentTask(userId,data);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getCommentsTask,
    addCommentTask,
    updateCommentTask,
    deleteCommentTask,
}