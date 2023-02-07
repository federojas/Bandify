import { Box, Button, Heading, Text, VStack } from '@chakra-ui/react';
import { CheckCircleIcon, EmailIcon } from '@chakra-ui/icons';
import { useTranslation } from 'react-i18next';

export default function ResetPwdEmailSent() {
  const { t } = useTranslation()
  return (
    <Box textAlign="center" py={10} px={6}>
      <VStack gap={'8'}>
        <VStack gap="2">
          <EmailIcon boxSize={'50px'} color={'green.500'} />
          <Heading as="h2" size={'2xl'}>
            {t("ResetPwdEmailSent.Title")}
          </Heading>
          <Heading as='h4' size='md' mb={6} >
            {t("ResetPwdEmailSent.Subtitle")}
          </Heading>
        </VStack>
        <Text color={'gray.500'} fontSize={'x-large'}>
          {t("ResetPwdEmailSent.Text")}
        </Text>
        <VStack gap='2'>

          <Text color={'gray.500'} as={'i'} fontSize={'large'}>
            {t("ResetPwdEmailSent.DidntReceive")}
          </Text>
          <Button colorScheme="blue" variant="outline">
            {t("ResetPwdEmailSent.Resend")}
          </Button>

        </VStack>
      </VStack>
    </Box>
  );
}