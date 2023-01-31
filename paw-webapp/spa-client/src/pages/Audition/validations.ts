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
};

export default newAuditionOptions;
