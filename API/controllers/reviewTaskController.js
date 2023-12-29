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

const getReviewsByTaskId = async (taskId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            SELECT * FROM [dbo].[Review] WHERE [TaskId] = @taskId;
        `;

        const result = await pool.request()
            .input('taskId', sql.Int, taskId)
            .query(query);

        return result.recordset;
    } catch (error) {
        return error.message;
    }
};


const addReviewTask = async (req, res) => {
    try {
        const reviewData = {
            TaskId: req.params.taskId,
            Rating: req.body.Rating,
            Comment: req.body.Comment
        };

        const created = await reviewTaskData.createReviewTask(reviewData);
        res.send(created);
    } catch (error) {
        res.status(400).send(error.message);
    }
};


const updateReviewTask = async (req, res)=> {
    try {
        const data = req.body;
        const created = await reviewTaskData.updateReviewTask(taskId, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteReviewTask = async (req, res)=> {
    try {
        const reviewId = req.params.reviewId;
        const data = req.body;
        const deleted = await reviewTaskData.deleteReviewTask(reviewId,data);
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
    getReviewsByTaskId
}
