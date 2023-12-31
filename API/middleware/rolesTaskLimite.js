'use strict'
const jwt = require('jsonwebtoken');
const userData = require('../data/userService');
const tarefaData = require('../data/tarefaService')
const utils = require('../utils/utils');

    const checkRoleTaskLimite = async (req,res, next) => {
        try {
            const user = jwt.verify(token, process.env.SECRET_TOKEN);
            const findUser = await userData.listUtilizadorById(user.user[0].Id);

            if (findUser[0].User_Roles === utils.user_roles.UR_Premium) {
                // Se o utilizador for premium, permitir o acesso sem limites
                next();
            } else {
                // Verificar o limite de criação de tarefas para utilizadores não premium
                const currentWeekTasks = await tarefaData.countTasksCreatedByUserInCurrentWeek(findUser[0].Id);

                if (currentWeekTasks < 5) {
                    next();
                } else {
                    return res.status(401).json({
                        error: `Limite de criação de tarefas atingido para o Utilizador (${findUser[0].Id}). Máximo de 5 tarefas por semana permitido para usuários não premium.`,
                    });
                }
            }
        } catch (error) {
            return res.status(401).json({error: `${error.message}`});
        }
    }
module.exports = {
    checkRoleTaskLimite
}