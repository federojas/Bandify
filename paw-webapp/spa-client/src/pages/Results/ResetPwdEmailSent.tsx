import {Box, Button, Heading, Text, useToast, VStack} from '@chakra-ui/react';
import { EmailIcon } from '@chakra-ui/icons';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { serviceCall } from "../../services/ServiceManager"
import {useUserService} from "../../contexts/UserService";
import {useLocation, useNavigate} from "react-router-dom";
import {UserPasswordResetRequestInput} from "../../api/types/User";

export default function ResetPwdEmailSent() {
  const [isDisabled, setIsDisabled] = useState(false);

  useEffect(() => {
    if (isDisabled) {
      const timerId = setTimeout(() => setIsDisabled(false), 20000);
      return () => clearTimeout(timerId);
    }
  }, [isDisabled]);

  const { t } = useTranslation()
  const userService = useUserService();
  const navigate = useNavigate();
  const toast = useToast();
  const location = useLocation();

  const onResend = () => {
    const input: UserPasswordResetRequestInput = {
      email: location.state.email
    }
    serviceCall(
        userService.generateUserPassword(input),
        navigate,
        () => {
        }
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Register.error"),
          status: "error",
          description: t("ResetPassword.error"),
          isClosable: true,
        });
      } else {
        toast({
          title: t("Register.success"),
          status: "success",
          description: t("ResetPassword.emailSent"),
          isClosable: true,
        })
      }
    });
  };

  return (
      <Box textAlign="center" py={10} px={6}>
        <VStack gap={'8'}>
          <VStack gap="2">
            <EmailIcon boxSize={'50px'} color={'green.500'}/>
            <Heading as="h2" size={'2xl'}>
              {t("ResetPwdEmailSent.Title")}
            </Heading>
            <Heading as='h4' size='md' mb={6}>
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
            <Button
                colorScheme="blue"
                variant="outline"
                isDisabled={isDisabled}
                onClick={() => {setIsDisabled(true); onResend();}}
            >
              {t("ResetPwdEmailSent.Resend")}
            </Button>

          </VStack>
        </VStack>
      </Box>
  );
};