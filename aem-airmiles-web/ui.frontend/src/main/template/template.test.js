const hostName = 'localhost';
const propertyText = 'localhost'

test('Active Nav Link matches host', () => {
    expect(propertyText).toMatch(hostName)
});