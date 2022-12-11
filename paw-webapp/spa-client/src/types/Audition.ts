type Audition = {
  band: {
    name: string;
    id: number;
  };
  id: number;
  creationDate: Date;
  title: string;
  roles: string[];
  genres: string[];
  location: string;
};

export default Audition;