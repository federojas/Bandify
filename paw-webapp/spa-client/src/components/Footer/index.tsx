import { FooterCopyright, Lang, PageFooter } from "./styles";

export default function Footer() {
  return (
    <PageFooter>
      <FooterCopyright>
        Copyright
        <div>
            <b>Contact us</b>
            <a href="mailto: ${mail}">a</a>
        </div>
        <div>
            <Lang href="">ES</Lang>
            <Lang href="">EN</Lang>
        </div>
      </FooterCopyright>
 
    </PageFooter>
  );
}
