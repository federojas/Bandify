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

export default registerOptions;
