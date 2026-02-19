# Randy

Randy is your personal task manager that lives in a chat window. Keep track of your
todos, deadlines, and events by just typing commands. Randy goes by the persona
**Vikkstar** and calls you **Deji** -- it's a whole vibe.

![Product Screenshot](Ui.png)

## Quick Start

1. Make sure you have **Java 17** installed on your machine.
2. Download the latest `randy.jar` from the [releases page](https://github.com/randalliasdanx/ip/releases).
3. Copy the jar to whatever folder you want to use as your working directory.
4. Open a terminal, navigate to that folder, and run:
   ```
   java -jar randy.jar
   ```
5. A chat window will appear with a welcome message. If you had tasks saved from before,
   they show up in that first message too.
6. Type a command in the text field and press Enter (or click Send).

## Command Format Notes

- Words in `UPPER_CASE` are parameters you need to supply.
  For example, `todo DESCRIPTION` means you type something like `todo buy groceries`.
- Dates must be in `yyyy-MM-dd` format (e.g. `2025-03-15`). Fake dates like `2025-02-30`
  will be rejected.
- Commands are **case-insensitive** -- `TODO`, `Todo`, and `todo` all do the same thing.

## Features

### Adding a todo: `todo`

Creates a simple task with no date attached.

Format: `todo DESCRIPTION`

Example: `todo read chapter 5`

```
LESGOOO! added that one Deji:
[T][ ] read chapter 5
you now have 1 tasks in the bag!
```

### Adding a deadline: `deadline`

Creates a task with a due date. Valid dates get formatted into a readable form
(e.g. `2025-03-20` becomes `Mar 20 2025`).

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline submit essay /by 2025-03-20`

```
LESGOOO! added that one Deji:
[D][ ] submit essay (by: Mar 20 2025)
you now have 2 tasks in the bag!
```

### Adding an event: `event`

Creates a task that spans a date range. The start date must not be after the end date.

Format: `event DESCRIPTION /from START /to END`

Example: `event hackathon /from 2025-04-01 /to 2025-04-03`

```
LESGOOO! added that one Deji:
[E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
you now have 3 tasks in the bag!
```

### Listing all tasks: `list`

Prints out all your tasks in numbered order.

Format: `list`

```
here's the lineup Deji:
1. [T][ ] read chapter 5
2. [D][ ] submit essay (by: Mar 20 2025)
3. [E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
```

### Marking a task as done: `mark`

Ticks off a task. The `[ ]` changes to `[X]`.

Format: `mark INDEX`

Example: `mark 1`

```
BIG W Deji! smashed it:
[T][X] read chapter 5
```

### Unmarking a task: `unmark`

Unticks a task so it goes back to not done.

Format: `unmark INDEX`

Example: `unmark 1`

```
alright Deji, unmarked this one:
[T][ ] read chapter 5
```

### Deleting a task: `delete`

Permanently removes a task from the list.

Format: `delete INDEX`

Example: `delete 2`

```
gone and dusted Deji:
[D][ ] submit essay (by: Mar 20 2025)
you now have 2 tasks
```

### Finding tasks by keyword: `find`

Searches all your tasks for a keyword (case-insensitive).

Format: `find KEYWORD`

Example: `find chapter`

```
found these for ya Deji:
1. [T][ ] read chapter 5
```

### Filtering tasks by date: `on`

Shows deadlines due on that date, and events whose range covers that date.

Format: `on DATE`

Example: `on 2025-04-02`

```
tasks on 2025-04-02 Deji:
1. [E][ ] hackathon (from: Apr 01 2025 to: Apr 03 2025)
```

### Exiting: `bye`

Closes the app.

Format: `bye`

## Error Handling

Randy handles bad input gracefully instead of crashing. Some things it catches:

- **Unknown commands** -- tells you it doesn't recognise the command.
- **Missing details** -- reminds you to include a description or date.
- **Wrong format** -- shows the expected format for deadline/event commands.
- **Invalid task number** -- tells you if you typed a non-number or out-of-range index.
- **Duplicate tasks** -- warns you if an identical task already exists.
- **Bad dates** -- rejects dates like Feb 30 that don't actually exist.
- **Start after end** -- rejects events where the start date comes after the end date.

Errors show up in red in the GUI so they're easy to spot.

## Data Storage

Your tasks get saved automatically to `./data/storage.txt` after every command.
If this file is missing when you open Randy, it just starts with an empty list.
If a line in the file is corrupted, Randy skips that line and loads the rest.

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| todo | `todo DESCRIPTION` | `todo wash dishes` |
| deadline | `deadline DESCRIPTION /by DATE` | `deadline report /by 2025-03-20` |
| event | `event DESCRIPTION /from START /to END` | `event camp /from 2025-06-01 /to 2025-06-03` |
| list | `list` | `list` |
| mark | `mark INDEX` | `mark 2` |
| unmark | `unmark INDEX` | `unmark 2` |
| delete | `delete INDEX` | `delete 3` |
| find | `find KEYWORD` | `find book` |
| on | `on DATE` | `on 2025-03-20` |
| bye | `bye` | `bye` |
