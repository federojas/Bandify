import { Tag } from "@chakra-ui/react";

const GenreTag = ({ genre }: { genre: string }) => {
  return (
    <Tag
      as="a"
      href={"#"}
      marginY={2}
      size={"lg"}
      variant="solid"
      colorScheme="cyan"
    >
      {genre}
    </Tag>
  );
};

export default GenreTag;
