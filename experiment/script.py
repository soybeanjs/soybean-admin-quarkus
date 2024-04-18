import logging
import msgpack
import requests

logging.basicConfig(level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')

response = requests.post(
    'http://localhost:8080/hello/msgPack',
    headers={
        'Content-Type': 'application/json',
        'Accept': '*/*'
    },
    json={"name": "123456789@qq.com", "age": 1}
)

data = response.content
logging.debug(f"Raw data length: {len(data)} bytes")
logging.debug(f"Raw data type: {type(data)}")
logging.debug(f"Raw data (binary): {data}")

try:
    decoded_data = msgpack.unpackb(data, raw=False)
    logging.debug(f"Decoded data: {decoded_data}")
except msgpack.exceptions.ExtraData as e:
    logging.error("Error in decoding data: More than one object encoded in the data.")
except msgpack.exceptions.UnpackValueError as e:
    logging.error("Error in decoding data: There are bytes left in the stream.")
except Exception as e:
    logging.error(f"An error occurred: {str(e)}")
