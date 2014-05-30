require 'sqlite3'

if File.exists?('tf.db')
	c = SQLite3::Database.open('tf.db')
	results = c.execute('SELECT value, COUNT(*) as C FROM words GROUP BY value ORDER BY C DESC LIMIT 25')
	c.close
	results.take(25).each { |w, f| puts "#{w} - #{f}" }
else
	puts "error: database does not exist (call write first)"
end