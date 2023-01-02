import { Tag } from "@chakra-ui/react";

const RoleTag = ({ role }: { role: string }) => {
  return (
    <Tag
      as="a"
      href={"#"}
      marginY={2}
      size={"lg"}
      variant="solid"
      colorScheme="teal"
    >
      {role}
    </Tag>
  );
};

export default RoleTag;
