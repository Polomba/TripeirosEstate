'use strict';
const config = require('../config');
const sql = require('mssql');
const utils = require('../utils/utils');

const listUtilizadores = async () => {
    try {
        let pool = await sql.connect(config.sql);
        let query =  'SELECT [Id],[Name],[Email]' +
            '[ProfilePicture],[Roles],[Token]' +
            'FROM [dbo].[User]';
        const list = await pool.request()
            .query(query);
        return list.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const listUtilizadorById = async (Id)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[Password]' +
            ',[Roles],[Token]' +
            'FROM [dbo].[User]' +
            'WHERE [Id] = @Id';

        const oneUtilizador = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);

        return oneUtilizador.recordset;
    }
    catch (error) {
        return  error.message;
    }
}



const listUtilizadorByEmail = async (Email)=> {
    try {
        let pool = await  sql.connect(config.sql);
        let query = 'SELECT [Id],[Name],[Email],[Password],[Roles],[Token],[ProfilePicture] ' +
            'FROM [dbo].[User] ' +
            'WHERE [Email] = @Email';


        const oneUtilizador = await pool.request()
            .input('Email', sql.VarChar(255), Email)
            .query(query);

        return oneUtilizador.recordset;
    }
    catch (error) {
        return  error.message;
    }
}

const updateRolesUtilizador = async (Id, Roles) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[User] SET Roles = @Roles ' +
            'WHERE [Id]=@Id';

        const update = await pool.request()
            .input('Id', sql.Int, Id)
            .input('Roles', sql.VarChar(255), Roles)
            .query(query);

        return update.recordset;
    }
    catch (error) {
        return error.message;
    }
}

const createUtilizador = async (userData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = `
            INSERT INTO [dbo].[User]
                ([Name], [Email], [Password], [Roles], [Token], [ProfilePicture])
            VALUES (@Name, @Email, @Password, @Roles, @Token, @ProfilePicture);
            SELECT SCOPE_IDENTITY() AS Id;
        `;

        let result = await pool.request()
            .input('Name', sql.VarChar(255), userData.Name)
            .input('Email', sql.VarChar(255), userData.Email)
            .input('Password', sql.VarChar(255), userData.Password)
            .input('Roles', sql.VarChar(255), utils.user_roles.UR_Normal)
            .input('Token', sql.VarChar(512), userData.Token)
            .input('ProfilePicture', sql.VarChar(255), userData.ProfilePicture)
            .query(query);
        return result.recordset;
    } catch (error) {
        throw new Error(`Erro ao criar novo usuário: ${error.message}`);
    }
};





const deleteUtilizador = async (Id) => {
    try {
        let pool = await sql.connect(config.sql);

        let deleteResidentsQuery = 'DELETE FROM [dbo].[Residents] WHERE [UserId] = @Id;';
        await pool.request().input('Id', sql.Int, Id).query(deleteResidentsQuery);

        let deleteTaskQuery = 'DELETE FROM [dbo].[Task] WHERE [UserId] = @Id;';
        await pool.request().input('Id', sql.Int, Id).query(deleteTaskQuery);

        let deletePaymentQuery = 'DELETE FROM [dbo].[Payment] WHERE [UserId] = @Id;';
        await pool.request().input('Id', sql.Int, Id).query(deletePaymentQuery);

        let deleteTaskParticipant = 'DELETE FROM [dbo].[TaskPartipants] WHERE [UserId] = @Id';
        await pool.request().input('Id', sql.Int, Id).query(deleteTaskParticipant);


        let query = 'DELETE FROM [dbo].[User] WHERE [Id] = @Id;';
        const deleted = await pool.request()
            .input('Id', sql.Int, Id)
            .query(query);

        return deleted.recordset;
    } catch (error) {
        console.error('Erro ao deletar usuário:', error.message);
        return { error: error.message };
    }
};


async function updateUserToken(userId, newToken) {
    try {
        let pool = await sql.connect(config);
        let result = await pool
            .request()
            .input('Token', sql.VarChar(512), newToken)
            .input('UserId', sql.Int, userId)
            .query('UPDATE [User] SET Token = @Token WHERE Id = @UserId');

        return result.rowsAffected;
    } catch (error) {
        throw new Error(`Erro ao atualizar token: ${error.message}`);
    }
}

module.exports={
    listUtilizadores,
    listUtilizadorById,
    listUtilizadorByEmail,
    updateRolesUtilizador,
    createUtilizador,
    deleteUtilizador,
}
