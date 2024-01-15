'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listReviewsTasks = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT Review.TaskId, Review.Id AS ReviewId, Rating, Comment ' +
            'FROM [dbo].[Review] ' +
            'JOIN [dbo].[Task] ON Task.Id = Review.TaskId';

        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}


const createReviewTask = async (taskId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            INSERT INTO [dbo].[Review] (TaskId, Rating, Comment)
            VALUES (@TaskId, @Rating, @Comment);
        `;

        const result = await pool.request()
            .input('TaskId', sql.Int, taskId)
            .input('Rating', sql.Float, data.Rating)
            .input('Comment', sql.VarChar(255), data.Comment)
            .query(query);

        return result.recordset;
    } catch (error) {
        return error.message;
    }
};


const updateReviewTask = async (uId, data) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Review] SET ';
        const inputParams = ['Comment', 'Rating'];
        for (const param of inputParams) {
            query += data[param] ? `${param} = @${param}, ` : '';
        }
        query = query.slice(0, -2); // remove trailing comma and space
        query +=` WHERE [Id]=@uId AND [TaskId]=@tId`

        const update = await pool.request()
            .input('uId', sql.Int, uId)
            .input('tId', sql.Int, data.tId)
            .input('Review', sql.VarChar(255), data.Comment)
            .input('Rating', sql.Real, data.Rating)
            .query(query);
        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const deleteReviewTask = async (uId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE FROM [dbo].[Review] WHERE [Id] = @uId';

        const update = await pool.request()
            .input('uId', sql.Int, uId)
            .query(query);

        return update.recordset;
    } catch (error) {
        return error.message;
    }
};


module.exports = {
    listReviewsTasks,
    createReviewTask,
    updateReviewTask,
    deleteReviewTask,
}
