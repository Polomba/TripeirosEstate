'use strict'
const jwt = require('jsonwebtoken');
const userData = require('../data/userService');
const tarefaData = require('../data/tarefaService')
const utils = require('../utils/utils');
const authData = require('../controllers/authController')

const checkRoleTaskLimite = async (req, res, next) => {
    try {
        let token;
        let authHeader = req.headers['authorization'];

        if (!authHeader)
            return res.status(401).json({ message: 'Token not provided' });

        if (authHeader.indexOf(' ') >= 0)
            token = authHeader.split(' ')[1];
        else
            token = authHeader

        const decodedToken = jwt. verify(token, process.env.SECRET_TOKEN);
        console.log(decodedToken);
        const userId = decodedToken.user[0].Id;
        console.log(userId);

        const findUser = await userData.listUtilizadorById(userId);

        if (findUser[0].User_Roles === utils.user_roles.UR_Premium) {
            return next();
        } else {
            const currentWeekTasks = await tarefaData.countTasksCreatedByUserInCurrentWeek(userId);

            if (currentWeekTasks < 5) {
                return next();
            } else {
                return res.status(401).json({
                    error: `Limite de criação de tarefas atingido para o Utilizador(${userId}).Máximo de 5 tarefas por semana permitido para usuários não premium.`,
                });
            }
        }
    } catch (error) {
        return res.status(401).json({ error: `${error.message}` });
    }
};

module.exports = {
    checkRoleTaskLimite
}