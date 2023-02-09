import { Box, Button, Flex, Heading, Text } from '@chakra-ui/react';
import { CloseIcon } from '@chakra-ui/icons';
import { CheckCircleIcon } from '@chakra-ui/icons';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function Success() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <Box textAlign="center" py={10} px={6}>
      <CheckCircleIcon boxSize={'50px'} color={'green.500'} />
      <Heading as="h2" size="xl" mt={6} mb={2}>
        {t("VerifySuccess.Title")}
      </Heading>
      <Text color={'gray.500'} fontSize={'lg'}>
        {t("VerifySuccess.Subtitle")}

      </Text>
      <Button colorScheme="blue" mt={6} onClick={() => {
        navigate('/')
      }}>
        {t("VerifySuccess.Button")}
      </Button>
    </Box>
  );
}


function Error() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <Box textAlign="center" py={10} px={6}>
      <Box display="inline-block">
        <Flex
          flexDirection="column"
          justifyContent="center"
          alignItems="center"
          bg={'red.500'}
          rounded={'50px'}
          w={'55px'}
          h={'55px'}
          textAlign="center">
          <CloseIcon boxSize={'20px'} color={'white'} />
        </Flex>
      </Box>
      <Heading as="h2" size="xl" mt={6} mb={2}>
      {t("VerifyError.Title")}
      </Heading>
      <Text color={'gray.500'} fontSize={'lg'}>
      {t("VerifyError.Subtitle")}

      </Text>
      <Button colorScheme="blue" mt={6} onClick={() => {
        navigate('/auditions')
      }}>
        {t("VerifyError.Button")}
      </Button>
    </Box>
  );
}

export default function Verify() {
  const [error, setError] = useState(false);

  return (
    <Box>
      {error ? <Error /> : <Success />}
    </Box>
  );
}