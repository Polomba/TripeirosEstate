'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils')


const getResidentsByHouseId = async (houseId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            SELECT u.Id as UserId, u.Name as UserName, r.Homeid, r.UserId
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

const addResident = async (houseId, email) => {
    try {

        const userId = await userService.listUtilizadorByEmail(email)
        if(!userId){
            throw new Error("Usuário não encontrado")
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
