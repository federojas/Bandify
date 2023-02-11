import { MoonIcon, SunIcon } from "@chakra-ui/icons";
import { Button, useColorMode } from "@chakra-ui/react";
import useSound from "use-sound";

const ToggleColorMode = () => {
  const { colorMode, toggleColorMode } = useColorMode();
  const [play] = useSound("/paw-2022a-03/lightswitch.mp3", {
    volume: 0.05,
    sprite: {
      on: [0, 300],
      off: [500, 300],
    },
  });


  const handleClick = () => {
    toggleColorMode();
    colorMode === "dark" ? play({ id: "on" }) : play({ id: "off" });
  };

  return (
    <Button onClick={handleClick} mx={4}>{colorMode === 'dark' ? <SunIcon color="orange.200" /> : <MoonIcon color="blue.700" />}</Button>
  );
}

export default ToggleColorMode;