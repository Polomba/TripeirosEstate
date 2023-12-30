'use strict';

const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils')

const listHouses = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'SELECT * '+
            'FROM [dbo].Home';

        const list = await pool.request().query(query);
        return list.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};

const createHouse = async (houseData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            INSERT INTO Home (Name, Adress)
            VALUES (@Name, @Adress)
        `;

        const result = await pool.request()
            .input('Name', sql.VarChar(255), houseData.Name)
            .input('Adress', sql.VarChar(255), houseData.Adress)
            .query(query);

        return result.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};

const updateHouse = async (houseId, updatedHouseData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[Home] SET ';
        const inputParams = ['Name', 'Adress'];

        for (const param of inputParams) {
            query += updatedHouseData[param] ? `${param} = @${param}, ` : '';
        }

        query = query.slice(0, -2);
        query += ` WHERE [id] = @houseId`;

        const update = await pool.request()
            .input('houseId', sql.Int, houseId)
            .input('Name', sql.VarChar(255), updatedHouseData.Name)
            .input('Adress', sql.VarChar(255), updatedHouseData.Adress)

            .query(query);

        return update.recordset;
    } catch (error) {
        throw new Error(error.message);
    }
};

const deleteHouse = async (houseId) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'DELETE [dbo].[Home] WHERE [id] = @houseId;';

        const deleted = await pool.request()
            .input('houseId', sql.Int, houseId)
            .query(query);

        return deleted.recordset;

    } catch (error) {
        throw new Error(error.message);
    }
};

module.exports = {
    listHouses,
    createHouse,
    updateHouse,
    deleteHouse,
};