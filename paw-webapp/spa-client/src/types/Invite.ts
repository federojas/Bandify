type Invite = {
  bandId: number;
  bandName: string;
  inviteDescription: string;
  membershipId: number;
  memberRoles: Array<{ name: string }>;
};

export default Invite;
