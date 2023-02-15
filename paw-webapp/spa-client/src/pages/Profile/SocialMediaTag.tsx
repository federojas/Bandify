import { Button } from "@chakra-ui/react";
import SocialMedia from "../../models/SocialMedia";
import {
  FaFacebook,
  FaInstagram,
  FaTwitter,
  FaYoutube,
  FaSoundcloud,
  FaSpotify,
} from "react-icons/fa";

const SocialMediaTag = ({ social }: { social: SocialMedia }) => {
  switch (social.type) {
    case "FACEBOOK": {
      return (
        <Button colorScheme={"facebook"} as="a" href={social.url} target="_blank">
          <FaFacebook />
        </Button>
      )
    }
    case "TWITTER": {
      return (
        <Button colorScheme={"twitter"} as="a" href={social.url} target="_blank">
          <FaTwitter />
        </Button>
      )
    }
    case "INSTAGRAM": {
      return (
        <Button colorScheme={"orange"} as="a" href={social.url} target="_blank">
          <FaInstagram />
        </Button>
      )
    }
    case "YOUTUBE": {
      return (
        <Button colorScheme={"red"} as="a" href={social.url} target="_blank">
          <FaYoutube />
        </Button>
      )
    }
    case "SOUNDCLOUD": {
      return (
        <Button colorScheme={"orange"} as="a" href={social.url} target="_blank">
          <FaSoundcloud />
        </Button>
      )
    }
    case "SPOTIFY": {
      return (
        <Button colorScheme={"yellow"} as="a" href={social.url} target="_blank">
          <FaSpotify />
        </Button>
      )
    }
    default: {
      return <></>;
    }
  }

}

export default SocialMediaTag;
