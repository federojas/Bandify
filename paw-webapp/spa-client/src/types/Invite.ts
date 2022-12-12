type Invite = {
  bandId: string;
  bandName: string;
  inviteDescription: string;
  membershipId: string;
  memberRoles: Array<{ name: string }>;
};

export default Invite;
