'use strict'

const reviewTaskData = require('../data/reviewTaskService');
const utils = require('../utils/utils');

const getReviewsTask = async (req, res) => {
    try {
        const revP = await reviewTaskData.listReviewsTasks();
        res.send(revP);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addReviewTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const created = await reviewTaskData.createReviewTask(userId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateReviewTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const created = await reviewTaskData.updateReviewTask(userId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteReviewTask = async (req, res)=> {
    try {
        const userId = req.params.userId;
        const data = req.body;
        const deleted = await reviewTaskData.deleteReviewTask(userId,data);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}
module.exports = {
    getReviewsTask,
    addReviewTask,
    updateReviewTask,
    deleteReviewTask,
}
