const addToBandOptions = {
  message: {
    required: "Description is required",
    maxLength: {
      value: 100,
      message: "Message cannot be larger than 100 characters",
    },
  },
};

const addToBandOptionsES = {
  message: {
    required: "La descripción es requerida",
    maxLength: {
      value: 100,
      message: "La descripción no puede ser mayor a 100 caracteres",
    },
  },
};

export {addToBandOptions, addToBandOptionsES};