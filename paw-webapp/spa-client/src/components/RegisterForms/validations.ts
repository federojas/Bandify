const registerOptions = {
  email: {
    required: "Email is required",
    maxLength: {
      value: 254,
      message: "Email cannot be larger than 254 characters",
    },
    pattern: /^\S+@\S+$/i,
  },
  password: {
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
  passwordConfirmation: {
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
  name: {
    required: "Name is required",
    maxLength: {
      value: 50,
      message: "Name cannot be larger than 50 characters",
    },
  },
  surname: {
    required: "Surname is required",
    maxLength: {
      value: 50,
      message: "Surname cannot be larger than 50 characters",
    },
  },
};

const registerOptionsES = {
  email: {
    required: "El correo electrónico es obligatorio",
    maxLength: {
      value: 254,
      message: "El correo electrónico no puede tener más de 254 caracteres",
    },
    pattern: /^\S+@\S+$/i,
  },
  password: {
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
  passwordConfirmation: {
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
  name: {
    required: "El nombre es obligatorio",
    maxLength: {
      value: 50,
      message: "El nombre no puede tener más de 50 caracteres",
    },
  },
  surname: {
    required: "El apellido es obligatorio",
    maxLength: {
      value: 50,
      message: "El apellido no puede tener más de 50 caracteres",
    },
  },
};

export {registerOptions, registerOptionsES};
