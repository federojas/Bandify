import { Flex, Icon, Tag } from "@chakra-ui/react"
import { FiArrowLeft } from "react-icons/fi"
import { useNavigate } from "react-router-dom";

const BackArrow = () => {

  const navigate = useNavigate();
    
  return (
      <Flex direction="row" alignItems="start" justify="center">
        <Icon as={FiArrowLeft} cursor="pointer" w={10} h={10} onClick={()=>{navigate(-1)}} />
      </Flex>
  )
}

export default BackArrow