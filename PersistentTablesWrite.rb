require 'sqlite3'

def create_db_schema(c)
	queries = <<-SQL
			CREATE TABLE IF NOT EXISTS documents (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);
			CREATE TABLE IF NOT EXISTS words (id INTEGER PRIMARY KEY AUTOINCREMENT, doc_id INTEGER, value INTEGER);
		SQL
	c.execute_batch(queries)
end

def load_file_into_db(path, c)
	def extract_words(path)
		s = File.read('../stop_words.txt').split(',')
		return File.read(path).downcase.scan(/[a-z]{2,}/).reject{|w| s.include? w}
	end
	words = extract_words(path)
	c.execute("INSERT INTO documents (name) VALUES (?)", path)	# doc name
	doc_id = c.get_first_value("SELECT id FROM documents WHERE name = ?", path)

	c.transaction	# one transaction
	words.each { |w| c.execute("INSERT INTO words (doc_id, value) VALUES (?, ?)", doc_id, w) }
	c.commit
end

File.delete('tf.db') if File.exists?('tf.db')	# create
c = SQLite3::Database.new('tf.db')				# opens, creates if it doesn't exist
create_db_schema(c)
load_file_into_db(ARGV.first, c)
c.close
puts 'tf.db created, data added'