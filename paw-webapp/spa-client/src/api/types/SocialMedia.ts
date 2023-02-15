
type SocialMedia = {
  id: number;
  self: string;
  type: string;
  url: string;
  user: string;
}

export type UpdateUserSocialMediaInput = {
  twitterUrl?: string;
  spotifyUrl?: string;
  instagramUrl?: string;
  facebookUrl?: string;
  youtubeUrl?: string;
  soundcloudUrl?: string;
};

export default SocialMedia;