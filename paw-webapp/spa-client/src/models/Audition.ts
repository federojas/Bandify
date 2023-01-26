export default interface Audition {
  id: number;
  creationDate: Date;
  title: string;
  lookingFor: string[];
  musicGenres: string[];
  location: string;
  description: string;
  applications: string;
  owner: string;
};