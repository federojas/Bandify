import { Tag } from "@chakra-ui/react";

const GenreTag = ({ genre, marginY = 2 }: { genre: string, marginY?: number }) => {
  return (
    <Tag
      as="a"
      marginY={marginY}
      size={"lg"}
      variant="solid"
      colorScheme="cyan"
    >
      {genre}
    </Tag>
  );
};

export const GenreCount = ({ count, marginY = 2 }: { count: number, marginY?: number }) => {
  return (
    <Tag
      marginY={marginY}
      size={"lg"}
      variant="solid"
      colorScheme="cyan"
    >
      +{count}
    </Tag>
  );
};

export default GenreTag;
