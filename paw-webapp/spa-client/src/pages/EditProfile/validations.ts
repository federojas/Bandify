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
};

export default editOptions;
