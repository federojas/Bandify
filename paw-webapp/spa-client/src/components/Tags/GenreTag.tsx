import { Tag } from "@chakra-ui/react";

const GenreTag = ({ genre, marginY = 2 }: { genre: string, marginY?: number }) => {
  return (
    <Tag
      as="a"
      href={"#"}
      marginY={marginY}
      size={"lg"}
      variant="solid"
      colorScheme="cyan"
    >
      {genre}
    </Tag>
  );
};

export default GenreTag;
