/**
 * 
 */
function BrokerService() {
	var cachedMessages = {};
	function putSingleMessage(receiver, message) {
		if (!receiver || !message) {
			return;
		}
		cachedMessages[receiver] = message;
	}
	
	function getSingleMessage(receiver) {
		return cachedMessages[receiver];
	}

	return {
		putSingleMessage: putSingleMessage,
		getSingleMessage: getSingleMessage
	}
}