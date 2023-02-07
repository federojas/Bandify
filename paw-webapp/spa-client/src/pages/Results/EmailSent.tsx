import { Box, Button, Heading, Text, VStack } from '@chakra-ui/react';
import { CheckCircleIcon, EmailIcon } from '@chakra-ui/icons';
import { useTranslation } from 'react-i18next';
import { useEffect, useState } from 'react';

export default function EmailSent() {
  const { t } = useTranslation()
  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    if (isDisabled) {
      const timerId = setTimeout(() => setIsDisabled(false), 20000);
      return () => clearTimeout(timerId);
    }
  }, [isDisabled]);

  return (
    <Box textAlign="center" py={10} px={6}>
      <VStack gap={'8'}>
        <VStack gap="2">
          <EmailIcon boxSize={'50px'} color={'green.500'} />
          <Heading as="h2" size={'2xl'}>
            {t("EmailSent.Title")}
          </Heading>
          <Heading as='h4' size='md' mb={6} >
            {t("EmailSent.Subtitle")}
          </Heading>
        </VStack>
        <Text color={'gray.500'} fontSize={'x-large'}>
          {t("EmailSent.Text")}
        </Text>
        <VStack gap='2'>

          <Text color={'gray.500'} as={'i'} fontSize={'large'}>
            {t("EmailSent.DidntReceive")}
          </Text>
          <Button colorScheme="blue" variant="outline"
            isDisabled={isDisabled}
            onClick={() => setIsDisabled(true)}

          >
            {t("EmailSent.Resend")}
          </Button>

        </VStack>
      </VStack>
    </Box>
  );
}