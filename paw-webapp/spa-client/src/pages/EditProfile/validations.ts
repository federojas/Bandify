const editOptions = {
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
  description: {
    maxLength: {
      value: 500,
      message: "Description cannot be larger than 500 characters",
    },
  },
};

const editOptionsES = {
  name: {
    required: "El nombre es requerido",
    maxLength: {
      value: 50,
      message: "El nombre debe tener 50 caracteres máximo",
    },
  },
  surname: {
    required: "El apellido es requerido",
    maxLength: {
      value: 50,
      message: "El apellido debe tener 50 caracteres máximo",
    },
  },
  description: {
    maxLength: {
      value: 500,
      message: "La descripción debe tener 500 caracteres máximo",
    },
  },
};

export {editOptions, editOptionsES};
