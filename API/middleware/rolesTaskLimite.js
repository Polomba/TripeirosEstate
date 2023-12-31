'use strict'
const jwt = require('jsonwebtoken');
const userData = require('../data/userService');
const tarefaData = require('../data/tarefaService')
const utils = require('../utils/utils');
const authData = require('../controllers/authController')

const checkRoleTaskLimite = async (req, res, next) => {
    try {
        const token = req.headers.authorization;
        if (!token) {
            return res.status(401).json({ error: 'Token não fornecido.' });
        }

        const decodedToken = jwt. verify(token, process.env.SECRET_TOKEN);
        const userId = decodedToken.user[0].Id;

        const findUser = await userData.listUtilizadorById(userId);

        if (findUser[0].User_Roles === utils.user_roles.UR_Premium) {
            return next();
        } else {
            const currentWeekTasks = await tarefaData.countTasksCreatedByUserInCurrentWeek(userId);

            if (currentWeekTasks < 5) {
                return next();
            } else {
                return res.status(401).json({
                    error: `Limite de criação de tarefas atingido para o Utilizador (${userId}). Máximo de 5 tarefas por semana permitido para usuários não premium.`,
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