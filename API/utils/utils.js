'use strict';

const user_roles = {
    UR_Normal: 'Normal',
    UR_Premium: 'Premium',
}

const paymentState = {
    PS_Pay: 'Pago',
    PS_NotPay: 'NaoPago'
}

const taskState = {
    TS_Active: 'Ativa',
    TS_Concluded: 'Concluída',
    TS_InProgress: 'Em Progresso'


}

module.exports = {
    user_roles,
    paymentState,
    taskState
}