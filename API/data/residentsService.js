'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils')
const userService = require("./userService");


const getResidentsByHouseId = async (houseId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            SELECT DISTINCT u.Id as UserId, u.Name as UserName, r.Homeid
            FROM Residents r
            INNER JOIN [User] u ON r.UserId = u.Id
            WHERE r.Homeid = @HouseId
        `;
        const result = await pool.request()
            .input('HouseId', sql.Int, houseId)
            .query(query);
        return result.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};


const addResident = async (houseId, userId) => {
    try {

        const user = await userService.listUtilizadorById(userId);
        if (user.length === 0) {
            throw new Error("Usuário não encontrado");
        }

        let pool = await sql.connect(config.sql);
        let query = 'INSERT INTO Residents (Homeid, UserId) VALUES (@HouseId, @UserId)';
        const result = await pool.request()
            .input('HouseId', sql.Int, houseId)
            .input('UserId', sql.Int, userId)
            .query(query);
        return result.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};

module.exports = {
    getResidentsByHouseId,
    addResident
};
