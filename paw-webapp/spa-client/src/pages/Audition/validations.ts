const newAuditionOptions = {
  title: {
    required: "Title is required",
    maxLength: {
      value: 50,
      message: "Title cannot be larger than 50 characters",
    },
  },
  description: {
    required: "Description is required",
    maxLength: {
      value: 300,
      message: "Description cannot be larger than 300 characters",
    },
  },
  roles: {
    required: "Select at least one role"
  }
};

const newAuditionOptionsES = {
  title: {
    required: "El titulo es requerido",
    maxLength: {
      value: 50,
      message: "El titulo no puede ser mayor a 50 caracteres",
    },
  },
  description: {
    required: "La descripción es requerida",
    maxLength: {
      value: 300,
      message: "La descripción no puede ser mayor a 300 caracteres",
    },
  },
};

const applyAuditionOptions = {
  message: {
    required: "Message is required",
    maxLength: {
      value: 300,
      message: "Message cannot be larger than 300 characters",
    },
  },
};

const applyAuditionOptionsES = {
  message: {
    required: "El mensaje es requerido",
    maxLength: {
      value: 300,
      message: "El mensaje no puede ser mayor a 300 caracteres",
    },
  },
};


export {newAuditionOptions, applyAuditionOptions, newAuditionOptionsES, applyAuditionOptionsES};
