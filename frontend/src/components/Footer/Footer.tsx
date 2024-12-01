import { Flex, Layout, Typography } from "antd";

const { Footer } = Layout;

const FooterComponent = () => {
  return (
    <Footer>
      <Flex align="center" justify="center">
        <Typography>© powered by darksecrets, 2024</Typography>
      </Flex>
    </Footer>
  )
}

export default FooterComponent
