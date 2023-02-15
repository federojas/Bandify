const newPasswordOptions = {
    newPassword: {
        required: "Password is required",
        minLength: {
            value: 8,
            message: "Password must be at least 8 characters",
        },
        maxLength: {
            value: 25,
            message: "Password cannot be larger than 25 characters",
        },
    },
    newPasswordConfirmation: {
        required: "Password confirmation is required",
        minLength: {
            value: 8,
            message: "Password must be at least 8 characters",
        },
        maxLength: {
            value: 25,
            message: "Password cannot be larger than 25 characters",
        },
    },
};

const newPasswordOptionsES = {
    newPassword: {
        required: "La contraseña es obligatoria",
        minLength: {
            value: 8,
            message: "La contraseña debe tener al menos 8 caracteres",
        },
        maxLength: {
            value: 25,
            message: "La contraseña no puede tener más de 25 caracteres",
        },
    },
    newPasswordConfirmation: {
        required: "La confirmación de la contraseña es obligatoria",
        minLength: {
            value: 8,
            message: "La contraseña debe tener al menos 8 caracteres",
        },
        maxLength: {
            value: 25,
            message: "La contraseña no puede tener más de 25 caracteres",
        },
    },
};

export {newPasswordOptions, newPasswordOptionsES};