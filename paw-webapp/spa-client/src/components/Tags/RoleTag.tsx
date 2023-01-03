import { Tag } from "@chakra-ui/react";

const RoleTag = ({ role, size = 'lg', marginY = 2 }: { role: string, size?: string, marginY?: number }) => {
  return (
    <Tag
      as="a"
      href={"#"}
      marginY={marginY}
      size={size}
      variant="solid"
      colorScheme="teal"
    >
      {role}
    </Tag>
  );
};

export default RoleTag;
